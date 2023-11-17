import { CartItem } from "./cart-item";
import { Product } from "./product";
import { User } from "./user";

export interface ShoppingCart{
  id:number;
  user:User;
  cartItems:Array<CartItem>;
  amount:number;
}
