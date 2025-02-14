DESCRIPTION = "libdatachannel is a C++ library for WebRTC data channels and media transport."
HOMEPAGE = "https://github.com/paullouisageneau/libdatachannel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=815ca599c9df247a0c7f619bab123dad"

SRC_URI = "gitsm://github.com/paullouisageneau/libdatachannel.git;protocol=https;branch=master"
SRCREV = "20bf658a60881854984f8ffb1586a4722bf590ec"

PV = "0.22.4"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg ${PN}-examples"

DEPENDS = "libnice"

RDEPENDS:${PN}-examples = "python3 python3-websockets"

do_install:append() {
    install -d ${D}/${bindir}/${PN}/examples/signaling-server-python
    install -m 0755 ${S}/examples/signaling-server-python/signaling-server.py ${D}/${bindir}/${PN}/examples/signaling-server-python
}

FILES:${PN} = " \
    ${libdir}/*.so.* \
    ${libdir}/cmake \
"

FILES:${PN}-dev = " \
    ${libdir}/*.so* \
    ${libdir}/*.so.${PV} \
    ${libdir}/cmake \
    ${includedir} \
"

FILES:${PN}-dbg = " \
    ${libdir}/.debug \
    ${libdir}/.debug/*.so* \
    ${libdir}/.debug/*.so.${PV} \
"

FILES:${PN}-examples = " \
    ${bindir}/${PN}/examples \
"

PACKAGECONFIG ??= "gnutls libjuice"
PACKAGECONFIG[openssl] = "-DUSE_GNUTLS=OFF -DUSE_MBEDTLS=OFF,-DUSE_GNUTLS=OFF -DUSE_MBEDTLS=OFF,openssl,,,gnutls mbedtls"
PACKAGECONFIG[gnutls] = "-DUSE_GNUTLS=ON -DUSE_MBEDTLS=OFF,-DUSE_GNUTLS=OFF -DUSE_MBEDTLS=OFF,gnutls,,,mbedtls openssl"
PACKAGECONFIG[mbedtls] = "-DUSE_GNUTLS=OFF -DUSE_MBEDTLS=ON,-DUSE_GNUTLS=OFF -DUSE_MBEDTLS=OFF,mbedtls,,,gnutls openssl"
# TODO: support using yocto's libnice/libjuice. Currently uses project's sources from submodules
PACKAGECONFIG[libjuice] = "-DUSE_NICE=OFF,-DUSE_NICE=ON,,,,libnice"
PACKAGECONFIG[libnice] = "-DUSE_NICE=ON,-DUSE_NICE=OFF,,,,libjuice"

# TODO: support options bellow
# option(BUILD_SHARED_LIBS "Build shared library" ON)
# option(BUILD_SHARED_DEPS_LIBS "Build submodules as shared libraries" OFF)
# option(PREFER_SYSTEM_LIB "Prefer system libraries over submodules" OFF)
# option(USE_SYSTEM_SRTP "Use system libSRTP" ${PREFER_SYSTEM_LIB})
# option(USE_SYSTEM_JUICE "Use system libjuice" ${PREFER_SYSTEM_LIB})
# option(USE_SYSTEM_USRSCTP "Use system libusrsctp" ${PREFER_SYSTEM_LIB})
# option(USE_SYSTEM_PLOG "Use system Plog" ${PREFER_SYSTEM_LIB})
# option(USE_SYSTEM_JSON "Use system Nlohmann JSON" ${PREFER_SYSTEM_LIB})
# option(NO_WEBSOCKET "Disable WebSocket support" OFF)
# option(NO_MEDIA "Disable media transport support" OFF)
# option(NO_EXAMPLES "Disable examples" OFF)
# option(NO_TESTS "Disable tests build" OFF)
# option(WARNINGS_AS_ERRORS "Treat warnings as errors" OFF)
# option(CAPI_STDCALL "Set calling convention of C API callbacks stdcall" OFF)
# option(SCTP_DEBUG "Enable SCTP debugging output to verbose log" OFF)
# option(RTC_UPDATE_VERSION_HEADER "Enable updating the version header" OFF)
