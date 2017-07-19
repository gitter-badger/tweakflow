import math, time from "std.tf"
alias math.nan? as nan?
alias math.min_long as min_long
alias math.max_long as max_long

library lib {
  f: (x) -> x
}

library operator_spec {

  nil_nil: nil - nil  == nil

  l0_l1: 0 - 1        == -1
  l1_l0: 1 - 0        == 1
  l1_l1: 1 - 1        == 0

  # integer overflow
  min_dec: min_long - 1 == max_long

  # going beyond double resolution, then converting back to long
  dmin_dec: min_long - 1.0 == min_long

  # extremes
  min_max: max_long - min_long == -1

  d0_d1: 0.0 - 1.0    == -1.0
  d1_d0: 1.0 - 0.0    == 1.0
  d1_d1: 1.0 - 1.0    == 0.0

  l0_d0: 0 - 0.0    == 0.0
  l0_d1: 0 - 1.0    == -1.0
  l1_d0: 1 - 0.0    == 1.0
  l1_d1: 1 - 1.0    == 0.0

  d0_l0: 0.0 - 0    == 0.0
  d0_l1: 0.0 - 1    == -1.0
  d1_l0: 1.0 - 0    == 1.0
  d1_l1: 1.0 - 1    == 0.0

  nil_l0: nil - 0     == nil
  l0_nil:   0 - nil   == nil

  nil_d0: nil - 0.0   == nil
  d0_nil: 0.0 - nil   == nil

  inf_inf:    nan?( Infinity - Infinity)  == true
  ninf_inf:   -Infinity - Infinity == -Infinity
  inf_ninf:   Infinity - -Infinity == Infinity
  ninf_ninf:  nan?(-Infinity - -Infinity) == true

  inf_d0:      Infinity - 0.0   == Infinity
  ninf_d0:    -Infinity - 0.0   == -Infinity
  d0_inf:      0.0 - Infinity   == -Infinity
  d0_ninf:     0.0 - -Infinity  == Infinity

  nan_nan:    nan?(NaN - NaN) == true
  nan_d0:     nan?(NaN - 0.0) == true
  d0_nan:     nan?(0.0 - NaN) == true

  foo_bar: try   "foo" - "bar"    catch "error" == "error"
  l0_bar:  try       0 - "bar"    catch "error" == "error"
  bar_l0:  try   "bar" - 0        catch "error" == "error"

  f_f:     try   lib.f - lib.f    catch "error" == "error"
  dt_dt:   try time.epoch - time.epoch catch "error" == "error"

}