import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from 'src/app/models/cart-item';
import { ShoppingCart } from 'src/app/models/shopping_cart';
import { Util } from 'src/app/public/services/util';
import { Request } from 'src/app/models/request';
import { AuthService } from 'src/app/public/services/auth.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  cart: ShoppingCart | null = null;
  items: CartItem[] = [];
  id:number = -1;

  constructor(private http: HttpClient,private route: ActivatedRoute){}

  ngOnInit(): void {
    if (AuthService .getToken('id')){
      this.id = Number(AuthService.getToken('id'));
    }
    this.getCart();
  }

  getCart():void{
    var body:Request = new Request(this.id,"empty");
    console.log(body);
    this.http.post<ShoppingCart>(Util.cartServerUrl,body).subscribe(result=>{
      console.log(result);
      this.cart = result;
      if(result != null){
        this.items = result.cartItems;
      }
      console.log(this.items);
    })
  }

  remove(item:CartItem):void{
    var body:Request = new Request(this.id,item.id);
    console.log(body);
    this.http.post<ShoppingCart>(Util.cartServerUrl+"/remove",body).subscribe(result=>{
      console.log(result);
      window.location.reload();
    })
  }

  checkout():void{
    var body:Request = new Request(this.id,"empty");
    console.log(body);
    this.http.post<ShoppingCart>(Util.cartServerUrl+"/checkout",body).subscribe(result=>{
      console.log(result);
      window.location.href='/profile'
    })
  }

  update(item: CartItem, quantity: number) {
    console.log(quantity)
    item.quantity += quantity;
    if(quantity==-1){
      item.amount -= item.product.price
    }else{
      item.amount += item.product.price
    }
    if(item.quantity==0){
      this.remove(item);
    }
    var body: Request = new Request(this.id, item);
    console.log(body);
    this.http.post<ShoppingCart>(Util.cartServerUrl+'/update',body).subscribe(result=>{
      console.log(result)
    })
  }
}
