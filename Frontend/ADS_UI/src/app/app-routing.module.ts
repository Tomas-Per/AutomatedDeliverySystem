import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then( m => m.HomePageModule)
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadChildren: () => import('./login/login.module').then( m => m.LoginPageModule)
  },
  {
    path: 'register',
    loadChildren: () => import('./register/register.module').then( m => m.RegisterPageModule)
  },
  {
    path: 'register-delivery',
    loadChildren: () => import('./register-delivery/register-delivery.module').then( m => m.RegisterDeliveryPageModule)
  },
  {
    path: 'order-details/:id/:orderCode',
    loadChildren: () => import('./order-details/order-details.module').then( m => m.OrderDetailsPageModule)
  },
  {
    path: 'tracking-search',
    loadChildren: () => import('./tracking-search/tracking-search.module').then( m => m.TrackingSearchPageModule)
  },
  {
    path: 'tracking-details/:id/:orderCode',
    loadChildren: () => import('./tracking-details/tracking-details.module').then( m => m.TrackingDetailsPageModule)
  },




];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
