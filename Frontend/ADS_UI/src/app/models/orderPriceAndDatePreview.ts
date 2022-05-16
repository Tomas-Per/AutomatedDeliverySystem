import { AddressPreview } from '../models/addressPreview';
import { Size } from './size';

export class OrderPriceAndDatePreview {
  sourceAddress: AddressPreview;
  destinationAddress: AddressPreview;
  isFragile: boolean;
  size: Size;
  isExpress: boolean;
}
