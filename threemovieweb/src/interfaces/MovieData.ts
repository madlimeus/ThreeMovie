export interface MovieDetail {
	movieId: string;
	netizenAvgRate: number;
	reservationRate: number;
	runningTime: number | undefined;
	admissionCode: string | undefined;
	country: string | undefined;
	reservationRank: string | undefined;
	totalAudience: string | undefined;
	summary: string | undefined;
	makingNote: string | undefined;
	nameKr: string;
	nameEn: string;
	releaseDate: number;
	poster: string | undefined;
	category: string;
	creators: [MovieCreator] | undefined,
	reviews: [MovieReview] | undefined,
	previews: [MoviePreview] | undefined
}

export interface MovieSearchData {
	movieId: string;
	netizenAvgRate: number;
	reservationRate: number;
	nameKr: string;
	nameEn: string | null;
	poster: string | null;
}

export interface MovieData {
	movieId: string;
	netizenAvgRate: number;
	reservationRate: number;
	nameKr: string;
	nameEn: string | null;
	poster: string | null;
	category: string | null;
	previews: [MoviePreview] | undefined;
}

export type MovieHeader = Pick<MovieDetail, "movieId" | "nameKr" | "nameEn" | "netizenAvgRate" | "reservationRate" | "releaseDate" | "poster" | "category" | "runningTime" | "admissionCode" | "country" | "reservationRank" | "totalAudience">;

export interface MovieReview {
	review: string | null;
	date: number;
	recommendation: string | null;
	movieTheater: string;
}

export interface MoviePreview {
	type: string;
	link: string;
}

export interface MovieCreator {
	nameKr: string | undefined;
	nameEn: string | undefined;
	roleKr: string | undefined;
	link: string | undefined;
}
