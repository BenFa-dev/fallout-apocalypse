import { inject, Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';

/** Les différents types de notification gérés. */
export type NotificationType = 'error' | 'warning' | 'info' | 'success';

/** Mappe chaque type de notification à son icône et à sa classe CSS. */
const NOTIFICATION_CONFIGS: Record<NotificationType, { icon: string; panelClass: string }> = {
  error: { icon: '✖', panelClass: 'error-snackbar' },
  warning: { icon: '⚠️', panelClass: 'warning-snackbar' },
  info: { icon: 'ℹ️', panelClass: 'info-snackbar' },
  success: { icon: '✔️', panelClass: 'success-snackbar' }
};

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private snackBar = inject(MatSnackBar);
  private translate = inject(TranslateService);

  showNotification(
    type: NotificationType,
    messageKeyOrText: string,
    params?: Record<string, unknown>,
    isDirectText = false,
    duration = 4000
  ): void {
    const { icon, panelClass } = NOTIFICATION_CONFIGS[type];

    if (isDirectText) {
      this.snack(messageKeyOrText, panelClass, icon, duration);
    }
    this.translate.get(messageKeyOrText, params).subscribe((translated: string) => {
      this.snack(translated, panelClass, icon, duration);
    });
  }

  private snack(message: string, panelClass: string, icon: string, duration = 4000) {
    this.snackBar.open(message, icon, {
      duration,
      panelClass: [panelClass],
      horizontalPosition: 'center',
      verticalPosition: 'bottom'
    });
  };

}
