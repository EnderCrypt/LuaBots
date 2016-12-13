package com.github.endercrypt.lua.library;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaNumber;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.endercrypt.luaj.extended.library.AbstractLuaLibrary;
import com.endercrypt.luaj.extended.library.LibraryFunction;
import com.endercrypt.luaj.extended.library.LuaLibrary;

@LuaLibrary("math")
public class MathExtended extends AbstractLuaLibrary
{
	@Override
	protected LuaTable obtainLuaTable()
	{
		return globals.get("math").checktable();
	}

	@LibraryFunction("round")
	public class Round extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			int argCount = args.narg();
			if ((argCount < 1) || (argCount > 2))
			{
				throw new LuaError("math.round requires either 1 or 2 arguments");
			}
			double value = args.arg(1).checkdouble();
			if (argCount == 1)
			{
				return rawRound(value, 1);
			}
			if (argCount == 2)
			{
				int decimals = args.arg(2).checkint();
				return round(value, decimals);
			}
			return LuaValue.varargsOf(new LuaValue[] { NIL });
		}

		private LuaNumber round(double value, int decimals)
		{
			int decimalZeros = (int) Math.pow(10, decimals);
			return rawRound(value, decimalZeros);
		}

		private LuaNumber rawRound(double value, int decimalZeros)
		{
			double rounded = Math.round(value * decimalZeros) / (double) decimalZeros;
			return LuaNumber.valueOf(rounded);
		}
	}

	@LibraryFunction("direction")
	public class Direction extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			double x1 = args.arg(1).checkdouble();
			double y1 = args.arg(2).checkdouble();
			double x2 = args.arg(3).checkdouble();
			double y2 = args.arg(4).checkdouble();
			double direction = Math.atan2(y2 - y1, x2 - x1);
			return LuaValue.varargsOf(new LuaValue[] { LuaNumber.valueOf(direction) });
		}
	}

	@LibraryFunction("distance")
	public class Distance extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			double x1 = args.arg(1).checkdouble();
			double y1 = args.arg(2).checkdouble();
			double x2 = args.arg(3).checkdouble();
			double y2 = args.arg(4).checkdouble();
			double x_diff = x1 - x2;
			double y_diff = y1 - y2;
			double distance = Math.sqrt(x_diff * x_diff + y_diff * y_diff);
			return LuaValue.varargsOf(new LuaValue[] { LuaNumber.valueOf(distance) });
		}
	}
}
