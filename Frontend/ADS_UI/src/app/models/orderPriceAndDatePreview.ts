import { AddressPreview } from './addressPreview';
import { Size } from './size';
import { User } from './user';

export class OrderPriceAndDatePreview {
  isFragile: boolean;
  size: Size;
  isExpress: boolean;
  sourceAddress: AddressPreview;
  destinationAddress: AddressPreview;
  destinationUser: User;
  sourceUser: User;
}
