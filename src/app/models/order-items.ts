import { Product } from "./product";

export interface OrderItems{
  id:number;
  product:Product;
  amount:number;
}

export class OrderItem{
  constructor(
    public product:Product,
    public amount:number
  ){}
}
