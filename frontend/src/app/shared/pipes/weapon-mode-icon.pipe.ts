import { Pipe, PipeTransform } from '@angular/core';
import { WeaponMode, WeaponModeIcons } from '@features/game/models/inventory/inventory.model';

@Pipe({ name: 'weaponModeIcon' })
export class WeaponModeIconPipe implements PipeTransform {
  transform(mode: WeaponMode | null | undefined): string {
    return mode?.modeType ? WeaponModeIcons[mode.modeType] : '';
  }
}
