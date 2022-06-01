import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { DatePipe } from '@angular/common';
import {AlertsInterceptorService} from './interceptors/alerts-interceptor.service';

@NgModule({
  declarations: [AppComponent],
  entryComponents: [],
  imports: [BrowserModule, IonicModule.forRoot(), AppRoutingModule, HttpClientModule],
  providers: [HttpClientModule, DatePipe, { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
    {provide: HTTP_INTERCEPTORS, useClass: AlertsInterceptorService, multi: true}],
  bootstrap: [AppComponent],
})
export class AppModule {}
