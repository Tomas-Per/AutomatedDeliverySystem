import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChangeTimeModalComponent } from '../change-time-modal/change-time-modal.component';
import { HomePage } from './home.page';

const routes: Routes = [
  {
    path: '',
    component: HomePage,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomePageRoutingModule {}
