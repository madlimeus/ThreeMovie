import {SyntheticEvent} from "react";
import moviePlaceHolder from '../assets/images/MoviePlaceHolder.jpg';

export const onErrorImg = (e: SyntheticEvent<HTMLImageElement, Event>) => {
	e.currentTarget.src = `${moviePlaceHolder}`;
};
