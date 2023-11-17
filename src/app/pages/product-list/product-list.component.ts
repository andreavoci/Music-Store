import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { ShoppingCart } from 'src/app/models/shopping_cart';
import { CartItem } from 'src/app/models/cart-item';
import { Request } from 'src/app/models/request';
import { Util } from 'src/app/public/services/util';
import { AuthService } from 'src/app/public/services/auth.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  public products: Product[] = [];

  constructor(private http: HttpClient, private route: ActivatedRoute){}

  ngOnInit(): void {
      this.getProducts();
  }

  public getProducts(): void{
    this.http.get<Product[]>(Util.productServerUrl).subscribe(result=>{
      console.log(result)
      this.products=result;
      this.products = this.products.sort((a,b)=> {
        if (a.title < b.title) {
          return -1;
        }
        if (a.title > b.title) {
          return 1;
        }
        return 0;
      });
    })
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
    }else {
      window.location.href = '/login';
    }
  }
}
