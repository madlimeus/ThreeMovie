export interface Theater {
	city: string;
	movieTheater: string;
	branches: [Branch]
}

export interface Branch {
	brchKr: string;
	brchEn: string;
	addrKr: string;
	addrEn: string | undefined;
}
