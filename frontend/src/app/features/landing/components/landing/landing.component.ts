import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@core/services/auth.service';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-landing',
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    TranslateModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatMenuModule
  ],
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent implements OnInit {
  router = inject(Router);
  authService = inject(AuthService);
  translate = inject(TranslateService);
  private route = inject(ActivatedRoute);
  private snackBar = inject(MatSnackBar);

  isAuthenticated = this.authService.isAuthenticated;

  features = [
    { key: 'character', icon: 'person' },
    { key: 'exploration', icon: 'explore' },
    { key: 'combat', icon: 'gavel' },
    { key: 'survival', icon: 'health_and_safety' }
  ];

  ngOnInit(): void {
    if (this.isAuthenticated()) {
      this.router.navigate(['/game']);
    }

    this.route.queryParams.subscribe(params => {
      if (params['authRequired']) {
        this.snackBar.open(
          this.translate.instant('auth.required'),
          this.translate.instant('common.close'),
          {
            duration: 5000,
            panelClass: ['error-snackbar']
          }
        );
      }
    });
  }

  login(): void {
    this.authService.login();
  }
}
