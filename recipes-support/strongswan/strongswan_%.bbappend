FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
        file://0001-adhere-to-IKE_SA-limit.patch \
        file://0002-charon-add-opt-src.patch \
        file://0003-vici-send-certs-for-ike_sa-events.patch \
        file://0004-vici-support-indiv-sa-state-changes.patch \
        file://0005-vici-support-async-initiation.patch \
        file://0006-terminate-by-src-and-dst.patch \
        file://libstrongswan.strongswan.logcheck.ignore.paranoid \
        file://libstrongswan.strongswan.logcheck.ignore.server \
        file://libstrongswan.strongswan.logcheck.ignore.workstation \
        file://libstrongswan.strongswan.logcheck.violations.ignore \
        file://strongswan-starter.ipsec.init \
        "

EXTRA_OECONF += " \
        --disable-blowfish --disable-des \
        --enable-agent \
        --enable-ctr --enable-ccm --enable-gcm --enable-addrblock \
        --with-capabilities=libcap \
        --enable-farp \
        --enable-dhcp \
        --enable-af-alg \
        --enable-vici \
        --enable-ha \
		--enable-led --enable-gcrypt \
		--enable-test-vectors \
		--enable-xauth-eap --enable-xauth-pam --enable-xauth-noauth \
        --enable-cmd \
		--enable-certexpire \
		--enable-lookip \
		--enable-error-notify \
		--enable-unity \
        --enable-eap-radius --enable-eap-identity --enable-eap-md5 \
		--enable-eap-gtc --enable-eap-aka --enable-eap-mschapv2 \
		--enable-eap-tls --enable-eap-ttls --enable-eap-tnc \
        "

# TODO: the following will cause install paths for charon, etc to match what they
# are on VyOS 1.2, however we may want to keep the original OE paths...
EXTRA_OECONF += " \
        --libexecdir=/usr/lib \
        "

#--with-user=strongswan --with-group=nogroup
#	--enable-kernel-pfkey --enable-kernel-klips \
# And for --enable-eap-sim we would need the library, which we don't
# have right now.
# Don't --enable-cisco-quirks, because some other IPsec implementations
# (most notably the Phion one) have problems connecting when pluto
# sends these Cisco options.


PACKAGECONFIG_append = " aesni swanctl connmark pkcs11 scep"
PACKAGECONFIG_remove = "sqlite3"

PACKAGECONFIG[mediation] = "--enable-mediation --enable-medcli --enable-medsrv, \
                            --disable-mediation --disable-medcli --disable-medsrv, \
                            clearsilver, clearsilver"
PACKAGECONFIG[connmark] = "--enable-connmark, --disable-connmark, iptables, iptables"
PACKAGECONFIG[pkcs11] = "--enable-pkcs11, --disable-pkcs11, ,${PN}-plugin-pkcs11"
PACKAGECONFIG[fast] = "--enable-fast, --disable-fast, ,${PN}-plugin-fast"
PACKAGECONFIG[tpm] = "--enable-tpm, --disable-tpm,"
PACKAGECONFIG[des] = "--enable-des, --disable-des, ,${PN}-plugin-des"

FILES_${PN} += "/usr/lib"

do_compile_append () {
    rm -rf ${D}/usr/sbin/.debug
}

RDEPENDS_${PN}_remove = "${PN}-plugin-des"
