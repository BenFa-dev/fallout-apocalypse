import { ItemInstance } from '@features/game/models/inventory/inventory.model';
import { patchState, signalStore, withMethods, withState } from '@ngrx/signals';

type ContextMenuType = 'slot' | 'list';

type ContextMenuState = {
	isOpen: boolean;
	targetElement: HTMLElement | null;
	itemInstance: ItemInstance | null;
	contextType: ContextMenuType | null;
};

const initialState: ContextMenuState = {
	isOpen: false,
	targetElement: null,
	itemInstance: null,
	contextType: null
};

export const ContextMenuStore = signalStore(
	{ providedIn: 'root' },
	withState(initialState),
	withMethods((store) => ({
		open: (params: { itemInstance: ItemInstance, target: HTMLElement, contextType: ContextMenuType }) =>
			patchState(store, {
				isOpen: true,
				itemInstance: params.itemInstance,
				targetElement: params.target,
				contextType: params.contextType
			}),

		close: () =>
			patchState(store, {
				isOpen: false,
				itemInstance: null,
				targetElement: null,
				contextType: null
			})
	}))
);
