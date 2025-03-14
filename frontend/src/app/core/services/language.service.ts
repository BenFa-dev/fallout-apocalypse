import { Injectable, signal } from '@angular/core';
import { SUPPORTED_LANGUAGES } from '@core/config/language.config';
import { Language } from '@core/models/language.model';
import { TranslateService } from '@ngx-translate/core';
import * as countryFlags from 'country-flag-icons/string/3x2';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  private readonly supportedLanguageCodes = Object.keys(SUPPORTED_LANGUAGES);
  readonly currentLanguage = signal(this.translateService.currentLang || 'fr');

  constructor(private translateService: TranslateService) {
    this.translateService.setDefaultLang('fr');
    this.setLanguage(this.currentLanguage());
  }

  getAvailableLanguages(): Language[] {
    return this.supportedLanguageCodes.map(code => {
      const langConfig = SUPPORTED_LANGUAGES[code];
      return {
        code: langConfig.code,
        label: this.translateService.instant(`languages.${ code }`),
        flag: countryFlags[langConfig.countryCode as keyof typeof countryFlags]
      };
    });
  }

  getCurrentLanguage(): Language {
    const code = this.currentLanguage();
    const langConfig = SUPPORTED_LANGUAGES[code];
    return {
      code: langConfig.code,
      label: this.translateService.instant(`languages.${ code }`),
      flag: countryFlags[langConfig.countryCode as keyof typeof countryFlags]
    };
  }

  setLanguage(langCode: string): void {
    if (this.supportedLanguageCodes.includes(langCode)) {
      this.translateService.use(langCode);
      this.currentLanguage.set(langCode);
    }
  }
}
