import { Component, computed, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectChange, MatSelectModule } from '@angular/material/select';
import { DomSanitizer } from '@angular/platform-browser';
import { LanguageService } from '@core/services/language.service';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-language-selector',
  imports: [
    TranslateModule,
    MatSelectModule,
    MatIconModule
  ],
  templateUrl: './language-selector.component.html',
  styleUrls: ['./language-selector.component.scss']
})
export class LanguageSelectorComponent {
  languageService = inject(LanguageService);
  sanitizer = inject(DomSanitizer);

  readonly availableLanguages = this.languageService.getAvailableLanguages().map(lang => ({
    ...lang,
    flag: this.sanitizer.bypassSecurityTrustHtml(lang.flag)
  }));

  protected selectedLanguage = computed(() => {
    const lang = this.languageService.getCurrentLanguage();
    return {
      ...lang,
      flag: this.sanitizer.bypassSecurityTrustHtml(lang.flag)
    };
  });

  onLanguageChange(event: MatSelectChange): void {
    this.languageService.setLanguage(event.value);
  }
}
