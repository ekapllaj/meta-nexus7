# the original packagegroup defines connman, switch to network manager
# this solves the error:
# 	Computing transaction...error: Can't install connman-gnome-0.7-r0@cortexa9hf_neon: unable to install provider for connman:
#	error:     connman-1.31-r0@cortexa9hf_neon is conflicted by networkmanager-1.0.10-r0@cortexa9hf_neon
NETWORK_MANAGER = "networkmanager"
