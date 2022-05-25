import { Address } from './address';
import { AddressPreview } from './addressPreview';
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
  sourceAddress: AddressPreview;
  destinationAddress: AddressPreview;
}
