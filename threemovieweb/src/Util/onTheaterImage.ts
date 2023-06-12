import CGV from "../assets/images/CGV.png";
import LC from "../assets/images/LotteCinema.png";
import MB from "../assets/images/MegaBox.png";

const onTheaterImage = (movieTheater: string) => {
	if (movieTheater === 'CGV') return CGV;
	if (movieTheater === 'LC') return LC;
	return MB;
};

export default onTheaterImage;
