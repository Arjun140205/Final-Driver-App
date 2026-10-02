import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, UrlTree } from '@angular/router';
import { AuthService } from '../../services/auth.service';

/**
 * Blocks manual navigation to pages the user may not see: anyone who is not logged in (or who
 * is logged in with the wrong role for the page) is redirected to the login page.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean | UrlTree {
    if (!this.authService.isLoggedIn()) {
      return this.router.createUrlTree(['/login']);
    }
    const requiredRole: string | undefined = route.data ? route.data['role'] : undefined;
    const userRole = (this.authService.getUserRole() || '').toLowerCase();
    if (requiredRole && userRole !== requiredRole.toLowerCase()) {
      return this.router.createUrlTree(['/login']);
    }
    return true;
  }
}

export { AuthGuard as AuthguardGuard };
