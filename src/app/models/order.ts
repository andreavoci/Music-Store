import { OrderItem } from './order-items';
export interface Order{
  id: number;
  userId: number;
  products: Array<OrderItem>;
  amount: number;
  purchaseTime: Date;
}
