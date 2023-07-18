package geomag

//go:generate bash -c "if [ ! -d libs ]; then mkdir libs; fi"
//go:generate gomobile bind -target android -androidapi 26 -javapkg com.sanmer -trimpath -v -o libs/geomag.aar

import (
	"github.com/proway2/go-igrf/igrf"
	"github.com/westphae/geomag/pkg/egm96"
	"github.com/westphae/geomag/pkg/wmm"
	"time"
)

//export igrf
func IGRF(lat, lon, alt, date float64) *MagneticField {
	data := igrf.New()
	mag, _ := data.IGRF(lat, lon, alt, date)

	return newMagneticField(
		mag.Declination,
		mag.DeclinationSV,
		mag.Inclination,
		mag.InclinationSV,
		mag.HorizontalIntensity,
		mag.HorizontalSV,
		mag.NorthComponent,
		mag.NorthSV,
		mag.EastComponent,
		mag.EastSV,
		mag.VerticalComponent,
		mag.VerticalSV,
		mag.TotalIntensity,
		mag.TotalSV,
	)
}

//export wmm
func WMM(lat, lon, alt, date float64) *MagneticField {
	tt := wmm.DecimalYear(date)
	loc := egm96.NewLocationGeodetic(lat, lon, alt)
	mag, _ := wmm.CalculateWMMMagneticField(loc, tt.ToTime())
	x, y, z, dx, dy, dz := mag.Ellipsoidal()

	return newMagneticField(
		mag.D(),
		mag.DD(),
		mag.I(),
		mag.DI(),
		mag.H(),
		mag.DH(),
		x,
		dx,
		y,
		dy,
		z,
		dz,
		mag.F(),
		mag.DF(),
	)
}

//export toDecimalYears
func ToDecimalYears(year, month, day, hour, min, sec, nsec int) float64 {
	dateTime := time.Date(year, time.Month(month), day,
		hour, min, sec, nsec, time.UTC)

	return float64(wmm.TimeToDecimalYears(dateTime))
}
