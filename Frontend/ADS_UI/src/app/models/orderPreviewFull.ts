import { Address } from './address';
import { AddressPreview } from './addressPreview';
import { Size } from './size';
import { User } from './user';

export class OrderPreviewFull {
  orderCode: string;
  isExpress: boolean;
  isFragile: boolean;
  size: Size;
  price: number;
  estimatedArrivalTime: string;
  sourceAddress: AddressPreview;
  destinationAddress: AddressPreview;
  sourceUser: User;
  destinationUser: User;
}
