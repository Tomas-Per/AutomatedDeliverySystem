import { Address } from '../models/address';
import { Size } from './size';

export class Order {
  sender: Address;
  receiver: Address;
  isFragile: boolean;
  size: Size;
  isExpress: boolean;
}
