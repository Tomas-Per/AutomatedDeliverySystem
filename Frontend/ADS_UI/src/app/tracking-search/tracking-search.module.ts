import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { TrackingSearchPageRoutingModule } from './tracking-search-routing.module';

import { TrackingSearchPage } from './tracking-search.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    TrackingSearchPageRoutingModule
  ],
  declarations: [TrackingSearchPage]
})
export class TrackingSearchPageModule {}
