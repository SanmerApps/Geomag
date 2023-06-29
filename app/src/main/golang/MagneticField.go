package geomag

type MagneticField struct {
	Declination         float64
	DeclinationSV       float64
	Inclination         float64
	InclinationSV       float64
	HorizontalIntensity float64
	HorizontalSV        float64
	NorthComponent      float64
	NorthSV             float64
	EastComponent       float64
	EastSV              float64
	VerticalComponent   float64
	VerticalSV          float64
	TotalIntensity      float64
	TotalSV             float64
}

func newMagneticField(
	declination, declinationSV,
	inclination, inclinationSV,
	horizontalIntensity, horizontalSV,
	northComponent, northSV,
	eastComponent, eastSV,
	verticalComponent, verticalSV,
	totalIntensity, totalSV float64,
) *MagneticField {
	return &MagneticField{
		Declination:         declination,
		DeclinationSV:       declinationSV,
		Inclination:         inclination,
		InclinationSV:       inclinationSV,
		HorizontalIntensity: horizontalIntensity,
		HorizontalSV:        horizontalSV,
		NorthComponent:      northComponent,
		NorthSV:             northSV,
		EastComponent:       eastComponent,
		EastSV:              eastSV,
		VerticalComponent:   verticalComponent,
		VerticalSV:          verticalSV,
		TotalIntensity:      totalIntensity,
		TotalSV:             totalSV,
	}
}
