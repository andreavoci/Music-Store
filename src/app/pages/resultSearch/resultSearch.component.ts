import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from 'src/app/models/cart-item';
import { Product } from 'src/app/models/product';
import { Request } from 'src/app/models/request';
import { AuthService } from 'src/app/public/services/auth.service';
import { Util } from 'src/app/public/services/util';

@Component({
  selector: 'app-resultSearch',
  templateUrl: './resultSearch.component.html',
  styleUrls: ['./resultSearch.component.css']
})
export class ResultSearchComponent implements OnInit {
  products:Product[] = [];

  constructor(private http:HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params=>{
      const key = params['encodedValue'];
      console.log(key);
      this.getProductsByValueTitle(key);
    })
  }

  getProductsByValueTitle(value:string){
    this.http.post<Product[]>(Util.productServerUrl+'/search/title/'+value,{}).subscribe(result=>{
      this.products=result;
      this.products = this.products.sort((a,b)=> {
        if (a.title < b.title) {
          return -1;
        }
        if (a.title > b.title) {
          return 1;
        }
        return 0;
    })
  });
}

  addToCart(p:Product){

    console.log(AuthService.getToken("id"))
    if(AuthService.getToken("id")){
      var userId:number = Number(AuthService.getToken("id"))

      var item:CartItem = new CartItem(p,1,p.price);

      var body:Request=new Request(userId,item);

      this.http.post(Util.cartServerUrl+"/add",body).subscribe(result =>{

      console.log(result)
      window.location.href='/cart';
    })
    }
  }

}
