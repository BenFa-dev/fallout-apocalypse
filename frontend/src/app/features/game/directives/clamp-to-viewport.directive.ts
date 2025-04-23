import { Directive, effect, ElementRef, inject } from '@angular/core';
import { ContextMenuStore } from '@features/game/stores/context-menu.store';

@Directive({
	selector: '[appContextMenuPosition]'
})
export class ContextMenuPositionDirective {
	private readonly el = inject(ElementRef<HTMLElement>);
	private readonly store = inject(ContextMenuStore);
	private readonly padding = 8;

	constructor() {
		const menu = this.el.nativeElement;
		menu.style.position = 'fixed';
		menu.style.left = '-9999px';
		menu.style.top = '-9999px';
		menu.style.opacity = '0';
		menu.style.transition = 'opacity 120ms ease, transform 120ms ease';

		effect(() => {
			const anchor = this.store.targetElement();
			if (!anchor) return;

			requestAnimationFrame(() => {
				const anchorRect = anchor.getBoundingClientRect();
				const menuRect = menu.getBoundingClientRect();

				let left = anchorRect.right;
				let top = anchorRect.top;

				if (left + menuRect.width > window.innerWidth - this.padding) {
					left = anchorRect.left - menuRect.width;
				}

				if (top + menuRect.height > window.innerHeight - this.padding) {
					top = window.innerHeight - menuRect.height - this.padding;
				}

				left = Math.max(this.padding, left);
				top = Math.max(this.padding, top);

				// Applique la position et affiche en douceur
				menu.style.transform = 'scale(0.9)';
				menu.style.opacity = '0';

				requestAnimationFrame(() => {
					menu.style.left = `${ left }px`;
					menu.style.top = `${ top }px`;
					menu.style.transform = 'scale(1)';
					menu.style.opacity = '1';
				});
			});
		});
	}
}
