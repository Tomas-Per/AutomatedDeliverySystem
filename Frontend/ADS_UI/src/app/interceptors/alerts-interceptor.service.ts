import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AlertsInterceptorService implements HttpInterceptor {

  constructor() {
  }

  protected static handleError(req: HttpRequest<any>, next: HttpHandler, error: HttpErrorResponse): Observable<any> {
    if (error.status === 412) {
      alert('Could not update information, because it was externally changed. Please try again.');
      return throwError(error);
    }

    if (error.status === 500) {
      alert('Unknown internal server error occurred');
      return throwError(error);
    }
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error) => AlertsInterceptorService.handleError(req, next, error)),
    );
  }
}

