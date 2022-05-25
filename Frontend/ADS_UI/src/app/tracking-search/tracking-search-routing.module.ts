import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TrackingSearchPage } from './tracking-search.page';

const routes: Routes = [
  {
    path: '',
    component: TrackingSearchPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TrackingSearchPageRoutingModule {}
