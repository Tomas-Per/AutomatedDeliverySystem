import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { OrderDetailsPageRoutingModule } from './order-details-routing.module';

import { OrderDetailsPage } from './order-details.page';
import { ChangeTimeModalComponent } from '../change-time-modal/change-time-modal.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    OrderDetailsPageRoutingModule
  ],
  declarations: [OrderDetailsPage, ChangeTimeModalComponent],
  entryComponents: [ChangeTimeModalComponent]
})
export class OrderDetailsPageModule {}
