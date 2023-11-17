import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Genre } from 'src/app/models/genre.enum';
import { Order } from 'src/app/models/order';
import { Product } from 'src/app/models/product';
import { Request } from 'src/app/models/request';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/public/services/auth.service';
import { Util } from 'src/app/public/services/util';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  userId = -1
  user : User | null = null
  orders: Order[] = [];
  products: Product[]= [];
  checkDelete : boolean = false;
  errorPopup : HTMLElement | null = null;
  errorPopupText = "";
  messageError = "";
  editing: boolean = false;
  productSelected: number[] = [];
  albumSelected: any = {}
  genres: string[] = this.enumValues(Genre);
  @ViewChild("dialogEdit") dialogEdit: ElementRef | undefined;

  constructor(private http: HttpClient) { }

  ngAfterViewInit() {
    this.errorPopup = document.getElementById("error-popup")
}

  ngOnInit() {
    if (AuthService.getToken('id')) {
      this.userId = Number(AuthService.getToken('id'));
    }
    const authToken = AuthService.getToken("token");

    this.getUser();
    this.getOrders();
    this.getProducts();
  }

  enumValues(enumType: any): string[]{
    return Object.keys(enumType).map(key => enumType[key]);
}

  getUser(){
    this.http.get<User>(Util.userServerUrl + "/" + this.userId).subscribe((result) => {
      this.user = result;
      console.log(result);
    });
  }

  logout(){
    console.log("delete")
    AuthService.deleteToken("token")
    AuthService.deleteToken("id")
    window.location.href = '/'
  }

  getOrders(): void{
    var authbody:Request = new Request(this.userId,"empty");
    console.log(authbody);
    this.http.post<Order[]>(Util.ordersServerUrl,authbody).subscribe(result=>{
      console.log(result);
      this.orders = result;
    })
  }

  getProducts(){
    this.http.get<Product[]>(Util.productServerUrl).subscribe(result=>{
      this.products = result;
      this.products = this.products.sort((a,b)=> a.id - b.id);
    })
  }

  onCheckChangeFor(event:any){
    if(event.target.checked){
        this.productSelected.push(event.target.value)
    }
    else{
        this.productSelected = this.productSelected.filter((f: any)=>f !== event.target.value)
    }
    this.messageError="";
    this.checkDelete=false;
    }

    onCheckChangeForn(event:any){
    if(event.target.checked){
        this.productSelected.push(event.target.value)
    }
    else{
        this.productSelected = this.productSelected.filter(c=>c !== event.target.value)
    }
    this.messageError="";
    this.checkDelete=false;
  }

  errorPopup_animation(text:string,visible:boolean){
    if(this.errorPopup){
      if(visible){
      this.errorPopup.style.top="-30px"
      }
      else{
      this.errorPopup.style.top="0"
      }
      this.errorPopupText=text;
    }
  }

  abortDeletingProduct(){
    this.messageError = ""
    this.checkDelete = false;
  }

  confirmDeletingProduct(){
    this.messageError = "Deleted."
    this.http.post<HttpResponse<String>>(Util.productServerUrl+"/delete",this.productSelected).subscribe(
      success => {
      window.location.reload()
      },
      error => {
      window.location.reload()
      }
    )
    this.checkDelete = false;
  }

  creaProduct(form: any) {
    this.http.post<Product>(Util.productServerUrl+"/create",form).subscribe(result=>{
      window.location.reload();
    })
  }

  deleteProduct(){
    if(this.productSelected.length==0){
      this.messageError = "Warning! Select first the products you want to delete."
      this.checkDelete = false;
      }
      else{
      this.messageError = "Are you sure you want to delete them?"
      this.checkDelete = true;
    }
  }

  editProduct() {
    if (this.productSelected.length == 0) {
        this.editing = false;
        this.messageError = "Warning! Select first the products you want to delete.";
    } else if (this.productSelected.length > 1) {
        this.editing = false;
        this.messageError = "Warning! Select only the product you want to delete.";
    } else {
      this.editing = true;
      this.albumSelected = this.products.find((p) => p.id == this.productSelected[0]);

      console.log(this.albumSelected);
      if (this.dialogEdit) {
      this.dialogEdit.nativeElement.showModal();
      }
    }
  }

  createProduct(form:any){
    this.http.post<Product>(Util.productServerUrl+"/create",form).subscribe(result=>{
      window.location.reload();
  })
  }

  update(form:any){
    form['id']=this.productSelected[0]
    const editedProduct : Product|null = form as Product;
    if(editedProduct){
        console.log(editedProduct)
    }
    this.http.post<Product>(Util.productServerUrl+"/update",form).subscribe(result=>{
        window.location.reload();
    })
  }

}
