import time as t from 'std.tf';
import expect, expect_error, to from "std/assert.tf";

alias t.hours_between as hours_between;

library hours_between_spec {

  of_default:
    expect(hours_between(), to.be_nil());

  of_start_nil:
    expect(hours_between(nil, t.epoch), to.be_nil());

  of_end_nil:
    expect(hours_between(t.epoch, nil), to.be_nil());

  same:
    expect(
      hours_between(1970-01-01T00:00:00, 1970-01-01T00:00:00),
      to.be(0)
    );

  one_sec:
    expect(
      hours_between(1970-01-01T00:00:00, 1970-01-01T00:00:01),
      to.be(0)
    );

  one_min:
    expect(
      hours_between(1970-01-01T00:00:00, 1970-01-01T00:01:00),
      to.be(0)
    );

  one_hour:
    expect(
      hours_between(1970-01-01T00:00:00, 1970-01-01T01:00:00),
      to.be(1)
    );

  one_hour_inverse:
    expect(
      hours_between(1970-01-01T01:00:00, 1970-01-01T00:00:00),
      to.be(-1)
    );

  one_day:
    expect(
      hours_between(1970-01-01T00:00:00, 1970-01-02T00:00:00),
      to.be(24)
    );

  one_day_inverse:
    expect(
      hours_between(1970-01-02T00:00:00, 1970-01-01T00:00:00),
      to.be(-24)
    );

  cross_zones:
    expect(
      hours_between(1970-01-01T00:00:00+04:00, 1970-01-01T00:00:00+03:00),
      to.be(1)
    );

}