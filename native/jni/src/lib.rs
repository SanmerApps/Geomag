use geomag::{DateTime, GeodeticLocation, Geomag, MagneticField, IGRF, WMM};
use jni::errors::Result;
use jni::objects::{JClass, JObject, JValue};
use jni::sys::{jdouble, jint};
use jni::JNIEnv;

pub trait JNI<'local> {
    fn set_double_field(&mut self, obj: &JObject, name: &str, val: jdouble) -> Result<()>;
    fn throw_illegal_decimal(self, decimal: f64) -> JObject<'local>;
    fn new_mf_object(self, m: &MagneticField) -> JObject<'local>;
}

impl<'a> JNI<'a> for JNIEnv<'a> {
    #[inline]
    fn set_double_field(&mut self, obj: &JObject, name: &str, val: jdouble) -> Result<()> {
        self.set_field(obj, name, "D", JValue::from(val))
    }

    fn throw_illegal_decimal(mut self, decimal: f64) -> JObject<'a> {
        self.throw_new(
            "java/lang/IllegalArgumentException",
            format!("decimal = {decimal}"),
        )
        .unwrap_or_else(|e| {
            eprintln!("{e}");
        });

        JObject::null()
    }

    fn new_mf_object(mut self, m: &MagneticField) -> JObject<'a> {
        match build_mf_object(&mut self, &m) {
            Ok(obj) => obj,
            Err(err) => {
                self.throw_new("java/lang/IllegalArgumentException", err.to_string())
                    .unwrap_or_else(|e| {
                        eprintln!("{e}");
                    });

                JObject::null()
            }
        }
    }
}

#[inline]
fn build_mf_object<'local>(env: &mut JNIEnv<'local>, m: &MagneticField) -> Result<JObject<'local>> {
    let class = env.find_class("dev/sanmer/geomag/MagneticField")?;
    let obj = env.alloc_object(class)?;

    env.set_double_field(&obj, "x", m.x)?;
    env.set_double_field(&obj, "xDot", m.x_dot)?;
    env.set_double_field(&obj, "y", m.y)?;
    env.set_double_field(&obj, "yDot", m.y_dot)?;
    env.set_double_field(&obj, "z", m.z)?;
    env.set_double_field(&obj, "zDot", m.z_dot)?;
    env.set_double_field(&obj, "h", m.h)?;
    env.set_double_field(&obj, "hDot", m.h_dot)?;
    env.set_double_field(&obj, "f", m.f)?;
    env.set_double_field(&obj, "fDot", m.f_dot)?;
    env.set_double_field(&obj, "d", m.d)?;
    env.set_double_field(&obj, "dDot", m.d_dot)?;
    env.set_double_field(&obj, "i", m.i)?;
    env.set_double_field(&obj, "iDot", m.i_dot)?;

    Ok(obj)
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_geomag_Geomag_toDecimalYears(
    _env: JNIEnv,
    _class: JClass,
    year: jint,
    month: jint,
    day: jint,
    hour: jint,
    minute: jint,
    second: jint,
) -> jdouble {
    let dt = DateTime::new(
        year as u32,
        month as u32,
        day as u32,
        hour as u32,
        minute as u32,
        second as u32,
    )
    .unwrap_unchecked();

    dt.decimal()
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_geomag_Geomag_wmm<'local>(
    env: JNIEnv<'local>,
    _class: JClass,
    longitude: jdouble,
    latitude: jdouble,
    altitude: jdouble,
    decimal: jdouble,
) -> JObject<'local> {
    let l = GeodeticLocation::new(longitude, latitude, altitude);
    let wmm = WMM::new(decimal);

    match wmm {
        None => env.throw_illegal_decimal(decimal),
        Some(m) => env.new_mf_object(&m.at_location(&l)),
    }
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_geomag_Geomag_igrf<'local>(
    env: JNIEnv<'local>,
    _class: JClass,
    longitude: jdouble,
    latitude: jdouble,
    altitude: jdouble,
    decimal: jdouble,
) -> JObject<'local> {
    let l = GeodeticLocation::new(longitude, latitude, altitude);
    let igrf = IGRF::new(decimal);

    match igrf {
        None => env.throw_illegal_decimal(decimal),
        Some(m) => env.new_mf_object(&m.at_location(&l)),
    }
}
