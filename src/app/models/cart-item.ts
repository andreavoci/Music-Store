import { Product } from './product';
export interface CartItem{
  id:number;
  product:Product;
  quantity:number;
  amount:number;
}
export class CartItem{
  constructor(
    public product:Product,
    public quantity:number,
    public amount:number
  ){}

}
