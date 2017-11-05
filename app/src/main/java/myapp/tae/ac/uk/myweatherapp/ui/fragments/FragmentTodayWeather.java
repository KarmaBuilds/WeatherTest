package myapp.tae.ac.uk.myweatherapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapp.tae.ac.uk.myweatherapp.R;
import myapp.tae.ac.uk.myweatherapp.constants.Constants;
import myapp.tae.ac.uk.myweatherapp.model.weather.WeatherInfo;

/**
 * Created by Karma on 5/11/17.
 */
public class FragmentTodayWeather extends Fragment {
    private static final String TAG = FragmentTodayWeather.class.getSimpleName();
    @BindView(R.id.tvWeatherHeaderCity)
    TextView tvWeatherHeaderCity;
    @BindView(R.id.tvWeatherHeaderDate)
    TextView tvWeatherHeaderDate;
    @BindView(R.id.tvWeatherHeaderDay)
    TextView tvWeatherHeaderDay;
    @BindView(R.id.tvWeatherTemp)
    TextView tvWeatherTemperature;
    @BindView(R.id.tvWeatherHeaderTempUnit)
    TextView tvWeatherTempUnit;
    @BindView(R.id.tvWeatherTempMinMax)
    TextView tvWeatherTempMinMax;
    @BindView(R.id.tvWeatherDescription)
    TextView tvWeatherDescription;
    @BindView(R.id.ivWeatherConIcon)
    ImageView ivWeatherConIcon;
    @BindView(R.id.tvWeatherSunriseValue)
    TextView tvWeatherSunriseValue;
    @BindView(R.id.tvWeatherSunsetValue)
    TextView tvWeatherSunsetValue;
    @BindView(R.id.tvWeatherPressureValue)
    TextView tvWeatherPressureValue;
    @BindView(R.id.tvWeatherHumidityValue)
    TextView tvWeatherHumidityValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_detail_layout, container, false);
        ButterKnife.bind(this, view);
        tvWeatherTempUnit.setText(Constants.CENTIGRADE_UNIT);
        return view;
    }

    public void updateViews(WeatherInfo weatherInfo) {
        tvWeatherHeaderCity.setText(weatherInfo.getName());
        updateDateAndDay();
        tvWeatherTemperature.setText(weatherInfo.getMain().getTemp().intValue() + "");
        Picasso.with(getActivity()).load(Constants.WEATHER_ICON_URL +
                weatherInfo.getWeather().get(0).getIcon() + Constants.IMAGE_PNG).into(ivWeatherConIcon);
        String tempMinMax = weatherInfo.getMain().getTempMin().intValue() +
                Constants.CENTIGRADE_UNIT + "/" + weatherInfo.getMain().getTempMax().
                intValue() + Constants.CENTIGRADE_UNIT;
        tvWeatherTempMinMax.setText(tempMinMax);
        tvWeatherDescription.setText(weatherInfo.getWeather().get(0).getDescription());
        tvWeatherSunriseValue.setText(getTime(weatherInfo.getSys().getSunrise()));
        tvWeatherSunsetValue.setText(getTime(weatherInfo.getSys().getSunset()));
        tvWeatherPressureValue.setText(weatherInfo.getMain().getPressure().toString() +
                Constants.PRESSURE_UNIT);
        tvWeatherHumidityValue.setText(weatherInfo.getMain().getHumidity() + Constants.PERCENTAGE);
    }

    private String getTime(Integer time) {
        Log.i(TAG, "getTime: " + time);
        Date date = new Date((long) time * 1000);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        timeFormat.setTimeZone(TimeZone.getDefault());
        return timeFormat.format(date);
    }

    private void updateDateAndDay() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateText = dateFormat.format(cal.getTime());
        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE");
        String dayOfWeekText = dayOfWeekFormat.format(cal.getTime());
        tvWeatherHeaderDate.setText(dateText);
        tvWeatherHeaderDay.setText(dayOfWeekText);
    }
}
