package ibrahim.example.stocklogger.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * <h3>This class is extended TextView for display negative and positive prices</h3>
 *
 * It will display in red text color if the price is negative;
 * It will display in blue text color if the price is positive;
 * It will display the user defined color if the price is 0.00
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 07/04/21
 * @see TextView
 */
public class PriceTextView extends androidx.appcompat.widget.AppCompatTextView {

    public PriceTextView(Context context) {
        super(context);
    }

    public PriceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PriceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        // if the text exist.
        if(text.length() > 0){
            String price = text.toString().substring(1);

            if(Double.parseDouble(price) < 0){
                setTextColor(Color.RED);
            }

            if(Double.parseDouble(price) > 0){
                setTextColor(Color.BLUE);
            }
        }
    }
}
