import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterDeliveryPage } from './register-delivery.page';

const routes: Routes = [
  {
    path: '',
    component: RegisterDeliveryPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RegisterDeliveryPageRoutingModule {}
