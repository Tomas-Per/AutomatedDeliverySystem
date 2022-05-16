import { Address } from './address';
import { Size } from './size';

export class OrderDetailed {
  id: number;
  orderCode: string;
  isExpress: boolean;
  isFragile: boolean;
  size: Size;
  price: number;
  date: string;
  estimatedArrivalTime: string;
  convenientArrivalTimeFrom: string;
  convenientArrivalTimeTo: string;
  sourceAddress: Address;
  destinationAddress: Address;
}