import MoviePreview from "./MoviePreview";

interface MovieData {
	movieId: string;
	netizenAvgRate: number;
	reservationRate: number;
	nameKR: string;
	nameEN: string | null;
	poster: string | null;
	category: string | null;
	previews: [MoviePreview];
}

export default MovieData;
