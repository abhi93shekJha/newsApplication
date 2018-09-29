package com.gsatechworld.gugrify.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.PostsByCategory;
import com.gsatechworld.gugrify.view.adapters.ReporterProfileRecyclerAdapter;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;
import com.gsatechworld.gugrify.view.dashboard.SearchActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReporterProfile extends AppCompatActivity {
    FloatingActionButton addNewPost;
    ImageView profileSearch;
    private Toolbar searchtollbar;
    private Toolbar toolbar;
    private Menu search_menu;
    private MenuItem item_search;
    private ArrayList<PostsByCategory> posts;
    private ReporterProfileRecyclerAdapter adapter;
    private LinearLayout linearLayoutProfile, linearLayoutPost;
    private CardView reporter_below_layout;
    private FloatingActionButton languageAndCityFloating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        setContentView(R.layout.activity_reporter_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setSearchtollbar();
//        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));

        linearLayoutProfile = (LinearLayout) findViewById(R.id.linearLayoutProfile);
        linearLayoutPost = (LinearLayout) findViewById(R.id.linearLayoutPost);
        reporter_below_layout = (CardView)findViewById(R.id.reporter_below_layout);
        languageAndCityFloating =(FloatingActionButton) findViewById(R.id.languageAndCityFloating);

        RecyclerView recyclerView = findViewById(R.id.reporter_profile_recycler);
        adapter = new ReporterProfileRecyclerAdapter(this, loadData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        profileSearch = findViewById(R.id.reporterProfileSearch);
        profileSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReporterProfile.this, SearchActivity.class);
                startActivity(intent);
            }
        });




        // clear FLAG_TRANSLUCENT_STATUS flag:
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.profile_pic_background_dark));
        }

        addNewPost = findViewById(R.id.languageAndCityFloating);
        addNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReporterProfile.this, ReporterPostActivity.class));
            }
        });
    }

    public ArrayList<PostsByCategory> loadData(){
        posts = new ArrayList<>();
        String image1 = "https://i2-prod.manchestereveningnews.co.uk/incoming/article13699191.ece/ALTERNATES/s615/unnamed-2.jpg";
        String image2 = "http://blog.typeathought.com/wp-content/uploads/2018/06/football.jpg";
        String image3 = "https://cdn-triplem.scadigital.io/media/46306/cricket-australia.jpg?preset=MainImage";

        String head1 = "Football news 1";
        String head2 = "Football news 2";
        String head3 = "Cricket news 3";

        String desc1 = "Kane played 573 minutes at the World Cup this summer, picking up six goals and the Golden Boot along the way.";
        String desc2 = "Southgate said: \"Harry falls in the category in which we have several players where we have to watch how much they play.\"";
        String desc3 = "After the two-and-a-half-day disaster at The Lord's last month, Virat Kohli's boys are now staring at another humiliation at the other end of London, The Oval.";

        String view1 = "15";
        String view2 = "19";
        String view3 = "25";

        String likes1 = "10";
        String likes2 = "15";
        String likes3 = "20";

        ArrayList<String> comments1 = new ArrayList<>();
        ArrayList<String> comments2 = new ArrayList<>();
        ArrayList<String> comments3 = new ArrayList<>();

        comments1.add("Good post!! Keep up the good work.");
        comments1.add("Nice post!!");
        comments1.add("Keep it up");

        comments1.add("Good post!! Keep up the good work.");
        comments1.add("Nice post!!");
        comments1.add("Keep it up!!");

        comments1.add("Good post!! Keep up the good work.");
        comments1.add("Nice post!!");
        comments1.add("Keep it up!!");

        ArrayList<String> text1 = new ArrayList<>();
        ArrayList<String> text2 = new ArrayList<>();
        ArrayList<String> text3 = new ArrayList<>();

        text1.add("Breaking News");
        text1.add("ಒಟಿ ತಂತ್ರಜ್ಞರ ಬೇಡಿಕೆಗೆ ತಕ್ಕಂತೆ ಹುದ್ದೆ ಭರ್ತಿ ಮಾಡಿ - ಯೂನಿಯನ್ ಆಗ್ರಹ");
        text1.add("ಬೆಂಗಳೂರು - ಆಪರೇಷನ್ ಥಿಯೇಟರ್ ತಂತ್ರಜ್ಞರ ಕೋರ್ಸ್ ಮುಗಿಸಿ ಹೊರ ಬರುವವರಿಗೆ ಉದ್ಯೋಗ ಸೃಷ್ಟಿಯಾಗದಿರುವ ಬಗ್ಗೆ ಅಭ್ಯರ್ಥಿಗಳಲ್ಲಿ ");
        text1.add("ವಸಂತನಗರದ ಗುರುನಾನಕ್ ಭವನದಲ್ಲಿ ಹಮ್ಮಿಕೊಂಡಿದ್ದ ಪದಾಧಿಕಾರಿಗಳ ಸಭೆಯಲ್ಲಿ ಮಾತನಾಡಿದರು. ");

        text2.add("Breaking News");
        text2.add("ಒಟಿ ತಂತ್ರಜ್ಞರ ಬೇಡಿಕೆಗೆ ತಕ್ಕಂತೆ ಹುದ್ದೆ ಭರ್ತಿ ಮಾಡಿ - ಯೂನಿಯನ್ ಆಗ್ರಹ");
        text2.add("ಬೆಂಗಳೂರು - ಆಪರೇಷನ್ ಥಿಯೇಟರ್ ತಂತ್ರಜ್ಞರ ಕೋರ್ಸ್ ಮುಗಿಸಿ ಹೊರ ಬರುವವರಿಗೆ ಉದ್ಯೋಗ ಸೃಷ್ಟಿಯಾಗದಿರುವ ಬಗ್ಗೆ ಅಭ್ಯರ್ಥಿಗಳಲ್ಲಿ ");
        text2.add("ವಸಂತನಗರದ ಗುರುನಾನಕ್ ಭವನದಲ್ಲಿ ಹಮ್ಮಿಕೊಂಡಿದ್ದ ಪದಾಧಿಕಾರಿಗಳ ಸಭೆಯಲ್ಲಿ ಮಾತನಾಡಿದರು. ");

        text3.add("Breaking News");
        text3.add("ಒಟಿ ತಂತ್ರಜ್ಞರ ಬೇಡಿಕೆಗೆ ತಕ್ಕಂತೆ ಹುದ್ದೆ ಭರ್ತಿ ಮಾಡಿ - ಯೂನಿಯನ್ ಆಗ್ರಹ");
        text3.add("ಬೆಂಗಳೂರು - ಆಪರೇಷನ್ ಥಿಯೇಟರ್ ತಂತ್ರಜ್ಞರ ಕೋರ್ಸ್ ಮುಗಿಸಿ ಹೊರ ಬರುವವರಿಗೆ ಉದ್ಯೋಗ ಸೃಷ್ಟಿಯಾಗದಿರುವ ಬಗ್ಗೆ ಅಭ್ಯರ್ಥಿಗಳಲ್ಲಿ ");
        text3.add("ವಸಂತನಗರದ ಗುರುನಾನಕ್ ಭವನದಲ್ಲಿ ಹಮ್ಮಿಕೊಂಡಿದ್ದ ಪದಾಧಿಕಾರಿಗಳ ಸಭೆಯಲ್ಲಿ ಮಾತನಾಡಿದರು. ");

        PostsByCategory p1 = new PostsByCategory("0", image1, head1, desc1, view1, likes1, comments1, text1);
        PostsByCategory p2 = new PostsByCategory("1", image2, head2, desc2, view2, likes2, comments2, text2);
        PostsByCategory p3 = new PostsByCategory("2", image3, head3, desc3, view3, likes3, comments3, text3);

        posts.add(p1);
        posts.add(p2);
        posts.add(p3);

        posts.add(p1);
        posts.add(p2);
        posts.add(p3);

        posts.add(p1);
        posts.add(p2);
        posts.add(p3);

        return posts;
    }

    private void setSearchtollbar() {
        {
            searchtollbar = (Toolbar) findViewById(R.id.searchtoolbar);
            if (searchtollbar != null) {
                searchtollbar.inflateMenu(R.menu.menu_search);
                search_menu = searchtollbar.getMenu();

                searchtollbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearLayoutPost.setVisibility(View.GONE);
                        linearLayoutProfile.setVisibility(View.GONE);
                        languageAndCityFloating.setVisibility(View.GONE);
                        reporter_below_layout.setVisibility(View.GONE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            circleReveal(R.id.searchtoolbar, 1, true, false);
                        else
                            searchtollbar.setVisibility(View.GONE);
                    }
                });

                item_search = search_menu.findItem(R.id.action_filter_search);

                MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            circleReveal(R.id.searchtoolbar, 1, true, false);
                            linearLayoutPost.setVisibility(View.VISIBLE);
                            linearLayoutProfile.setVisibility(View.VISIBLE);
                            languageAndCityFloating.setVisibility(View.VISIBLE);
                            reporter_below_layout.setVisibility(View.VISIBLE);
                        } else
                            searchtollbar.setVisibility(View.GONE);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        linearLayoutPost.setVisibility(View.GONE);
                        linearLayoutProfile.setVisibility(View.GONE);
                        languageAndCityFloating.setVisibility(View.GONE);
                        reporter_below_layout.setVisibility(View.GONE);
                        return true;
                    }
                });

                initSearchView();


            } else
                Log.d("toolbar", "setSearchtollbar: NULL");
        }
    }

    public void initSearchView()
    {
        final SearchView searchView =
                (SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();

        // Enable/Disable Submit button in the keyboard

        searchView.setSubmitButtonEnabled(false);

        // Change search close button image

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.ic_clear);


        // set hint and the text colors

        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint("Search..");
        txtSearch.setHintTextColor(Color.DKGRAY);
        txtSearch.setTextColor(getResources().getColor(R.color.colorPrimary));


        // set the cursor

        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            public void callSearch(String query) {
                //Do searching
                Log.i("query", "" + query);

                List<PostsByCategory> temp = new ArrayList();
                for(PostsByCategory d: posts){
                    //or use .equal(text) with you want equal match
                    //use .toLowerCase() for better matches
                    if(d.getNews_headlines().toLowerCase().contains(query.toLowerCase())){
                        temp.add(d);
                    }
                }
                //update recyclerview
                adapter.updateList(temp);

            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow)
    {
        final View myView = findViewById(viewID);

        int width=myView.getWidth();

        if(posFromRight>0)
            width-=(posFromRight*getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material))-(getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)/ 2);
        if(containsOverflow)
            width-=getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx=width;
        int cy=myView.getHeight()/2;

        Animator anim;
        if(isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0,(float)width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float)width, 0);

        anim.setDuration((long)220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(!isShow)
                {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if(isShow)
            myView.setVisibility(View.VISIBLE);

        // start the animation
        anim.start();


    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.searchtoolbar,1,true,true);
                else
                    searchtollbar.setVisibility(View.VISIBLE);

                item_search.expandActionView();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
