import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { TrackingDetailsPageRoutingModule } from './tracking-details-routing.module';

import { TrackingDetailsPage } from './tracking-details.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    TrackingDetailsPageRoutingModule
  ],
  declarations: [TrackingDetailsPage]
})
export class TrackingDetailsPageModule {}
