import math as m from 'std.tf';
import expect, expect_error, to from "std/assert.tf";

alias m.inc as inc;

library inc_spec {

  of_default:
    expect(inc(), to.be_nil());

  of_nil:
    expect(inc(nil), to.be_nil());

  of_zero_long:
    expect(inc(0), to.be(1));

  of_NaN:
    expect(inc(NaN), to.be_NaN());

  of_one_long:
    expect(inc(1), to.be(2));

  of_neg_one_long:
    expect(inc(-1), to.be(0));

  of_zero_double:
    expect(inc(0.0), to.be(1.0));

  of_one_double:
    expect(inc(1.0), to.be(2.0));

  of_neg_one_double:
    expect(inc(-1.0), to.be(0.0));

  of_max_long:
    expect(inc(m.max_long), to.be(m.min_long));

  of_max_long_as_double:
    expect(inc(m.max_long as double), to.be(m.max_long as double + 1.0));

  of_non_numeric:
    expect_error(
      () -> inc("foo"),
      to.have_code("ILLEGAL_ARGUMENT")
    );

}