export interface BookShowTime {
	movieKr: string;
	movieTheater: string;
	brchKr: string;
	brchEn: string | undefined;
	date: number;
	totalSeat: number;
	playKind: string | undefined;
	screenKr: string | undefined;
	screenEn: string | undefined;
	addrKr: string;
	addrEn: string | undefined;
	res: ShowTimeItem[];
}

export interface ShowTimeItem {
	startTime: number;
	endTime: number;
	restSeat: number;
	ticketPage: string;
}

export interface BookBrch {
	city: string;
	items: TheaterItem[];
}

export interface TheaterItem {
	movieTheater: string;
	brchKr: string;
	brchEn: string | undefined;
	addrKr: string;
	addrEn: string | undefined;
}

export interface BookMovie {
	movieId: string;
	movieKr: string;
	movieEn: string | undefined;
	category: string | undefined;
	runningTime: string | undefined;
	country: string | undefined;
	poster: string | undefined;
	reservationRank: string | undefined;
}

export interface BookDate {
	date: number;
}
