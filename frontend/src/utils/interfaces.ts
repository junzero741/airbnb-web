import React from "react";

export interface EmptyInterface {}

export interface SearchFilterInterface {
	type: string;
	isEnd: boolean;
	input?: string;
	placeholder?: string;
	isCalendarModalOn?: boolean;
	setIsCalendarModalOn?: any;
	isFeeModalOn?: boolean;
	setIsFeeModalOn?: any;
	isGuestModalOn?: boolean;
	setIsGuestModalOn?: any;
	isLocationModalOn?: boolean;
	setIsLocationModalOn?: any;
}

export interface ModalInterface {
	type: string;
	setInplaceHolder: React.Dispatch<React.SetStateAction<string | undefined>>;
	className?: string;
	isActive: boolean;
	setModalOn: any;
}
