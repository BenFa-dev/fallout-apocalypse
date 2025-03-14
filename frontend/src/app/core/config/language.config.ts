import { LanguageConfig } from '@core/models/language.model';

export const SUPPORTED_LANGUAGES: Record<string, LanguageConfig> = {
  fr: {
    code: 'fr',
    countryCode: 'FR'
  },
  en: {
    code: 'en',
    countryCode: 'GB'
  }
};
