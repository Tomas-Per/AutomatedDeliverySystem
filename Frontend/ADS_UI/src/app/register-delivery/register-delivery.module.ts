import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RegisterDeliveryPageRoutingModule } from './register-delivery-routing.module';

import { RegisterDeliveryPage } from './register-delivery.page';
import { AddressModalComponent } from '../address-modal/address-modal.component';
import { OrderConfimationModalComponent } from '../order-confimation-modal/order-confimation-modal.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    RegisterDeliveryPageRoutingModule
  ],
  declarations: [RegisterDeliveryPage, AddressModalComponent, OrderConfimationModalComponent],
  entryComponents: [AddressModalComponent, OrderConfimationModalComponent]
})
export class RegisterDeliveryPageModule {}
