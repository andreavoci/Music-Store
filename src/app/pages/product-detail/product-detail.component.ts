import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from 'src/app/models/cart-item';
import { Product } from 'src/app/models/product';
import { Request } from 'src/app/models/request';
import { AuthService } from 'src/app/public/services/auth.service';
import { Util } from 'src/app/public/services/util';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent{
  idProduct:number = -1;
  productSelected: Product | null = null;
  quantity: number = 0;
  tracklist:(string)[] = [];

  constructor(private http: HttpClient, private route: ActivatedRoute) {
    if (this.route.snapshot.paramMap.get('id')){
      this.idProduct = Number(this.route.snapshot.paramMap.get('id'));
    }

    this.getProduct(this.idProduct);
  }

  getProduct(id:number){
    return this.http.get<Product>(Util.productServerUrl+"/"+id).subscribe(result =>{
      this.productSelected=result;
      this.tracklist=this.splitTracklist(this.productSelected.tracklist)
      console.log(result);
    })
  }

  splitTracklist(tracklist:string){
    const arraySongs = tracklist.split(',');
    const listNumbered = arraySongs.map((track, index) => `${(index + 1)}. ${track}`);
    return listNumbered;
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
    } else {
      window.location.href = '/login';
    }
  }

}
