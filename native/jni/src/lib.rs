use geomag::model::{IGRF, WMM};
use geomag::num::Unit;
use geomag::{DateTime, GeodeticLocation, Geomag, MagneticField};
use jni::errors::Result;
use jni::objects::{JClass, JObject, JValue};
use jni::sys::{jdouble, jint};
use jni::JNIEnv;

macro_rules! set_double_field {
    ($self:expr, $obj:expr, $name:expr, $val:expr) => {
        $self.set_field($obj, $name, "D", JValue::from($val))
    };
}

#[inline]
fn throw_illegal_decimal<'local>(env: &mut JNIEnv<'local>, decimal: f64) -> JObject<'local> {
    env.throw_new(
        "java/lang/IllegalArgumentException",
        format!("decimal = {decimal}"),
    )
    .unwrap_or_else(|e| {
        eprintln!("{e}");
    });

    JObject::null()
}

#[inline]
fn build_mf_object<'local>(env: &mut JNIEnv<'local>, m: &MagneticField) -> Result<JObject<'local>> {
    let class = env.find_class("dev/sanmer/geomag/MagneticField")?;
    let obj = env.alloc_object(class)?;

    set_double_field!(env, &obj, "x", m.x)?;
    set_double_field!(env, &obj, "xDot", m.x_dot)?;
    set_double_field!(env, &obj, "y", m.y)?;
    set_double_field!(env, &obj, "yDot", m.y_dot)?;
    set_double_field!(env, &obj, "z", m.z)?;
    set_double_field!(env, &obj, "zDot", m.z_dot)?;
    set_double_field!(env, &obj, "h", m.h)?;
    set_double_field!(env, &obj, "hDot", m.h_dot)?;
    set_double_field!(env, &obj, "f", m.f)?;
    set_double_field!(env, &obj, "fDot", m.f_dot)?;
    set_double_field!(env, &obj, "d", m.d.v())?;
    set_double_field!(env, &obj, "dDot", m.d_dot.v())?;
    set_double_field!(env, &obj, "i", m.i.v())?;
    set_double_field!(env, &obj, "iDot", m.i_dot.v())?;

    Ok(obj)
}

#[inline]
fn new_mf_object<'local>(env: &mut JNIEnv<'local>, m: &MagneticField) -> JObject<'local> {
    match build_mf_object(env, &m) {
        Ok(obj) => obj,
        Err(err) => {
            env.throw_new("java/lang/IllegalArgumentException", err.to_string())
                .unwrap_or_else(|e| {
                    eprintln!("{e}");
                });

            JObject::null()
        }
    }
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
    mut env: JNIEnv<'local>,
    _class: JClass,
    longitude: jdouble,
    latitude: jdouble,
    altitude: jdouble,
    decimal: jdouble,
) -> JObject<'local> {
    let l = GeodeticLocation::new(longitude.deg(), latitude.deg(), altitude);
    let wmm = WMM::new(decimal);

    match wmm {
        None => throw_illegal_decimal(&mut env, decimal),
        Some(m) => new_mf_object(&mut env, &m.at_location(&l)),
    }
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_geomag_Geomag_igrf<'local>(
    mut env: JNIEnv<'local>,
    _class: JClass,
    longitude: jdouble,
    latitude: jdouble,
    altitude: jdouble,
    decimal: jdouble,
) -> JObject<'local> {
    let l = GeodeticLocation::new(longitude.deg(), latitude.deg(), altitude);
    let igrf = IGRF::new(decimal);

    match igrf {
        None => throw_illegal_decimal(&mut env, decimal),
        Some(m) => new_mf_object(&mut env, &m.at_location(&l)),
    }
}
