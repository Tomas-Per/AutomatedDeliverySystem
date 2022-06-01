import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TrackingDetailsPage } from './tracking-details.page';

const routes: Routes = [
  {
    path: '',
    component: TrackingDetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TrackingDetailsPageRoutingModule {}
